/*
 *  Copyright 2022 Red Hat
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.jboss.hal.client.patching;

import java.util.ArrayList;
import java.util.List;

import org.jboss.hal.config.Environment;
import org.jboss.hal.core.runtime.TopologyTasks;
import org.jboss.hal.core.runtime.host.Host;
import org.jboss.hal.dmr.Operation;
import org.jboss.hal.dmr.ResourceAddress;
import org.jboss.hal.dmr.dispatch.Dispatcher;
import org.jboss.hal.flow.FlowContext;
import org.jboss.hal.flow.Progress;
import org.jboss.hal.flow.Task;

import elemental2.promise.Promise;

import static java.util.stream.Collectors.toList;
import static org.jboss.hal.dmr.ModelDescriptionConstants.CORE_SERVICE;
import static org.jboss.hal.dmr.ModelDescriptionConstants.CORE_SERVICE_PATCHING;
import static org.jboss.hal.dmr.ModelDescriptionConstants.INCLUDE_RUNTIME;
import static org.jboss.hal.dmr.ModelDescriptionConstants.PATCHING;
import static org.jboss.hal.dmr.ModelDescriptionConstants.READ_RESOURCE_OPERATION;
import static org.jboss.hal.dmr.ModelDescriptionConstants.RECURSIVE;
import static org.jboss.hal.flow.Flow.sequential;

final class PatchTasks {

    /**
     * Reads the hosts and its patches.
     *
     * <p>
     * The context is populated with the following keys:
     * <ul>
     * <li>{@link TopologyTasks#HOSTS}: The ordered list of hosts with the domain controller as first element. Each host
     * contains its patches.</li>
     * </ul>
     */
    static List<Task<FlowContext>> patches(Environment environment, Dispatcher dispatcher) {
        List<Task<FlowContext>> tasks = new ArrayList<>(TopologyTasks.hosts(environment, dispatcher));
        tasks.add(new Patches(dispatcher));
        return tasks;
    }

    private static final class Patches implements Task<FlowContext> {

        private final Dispatcher dispatcher;

        Patches(Dispatcher dispatcher) {
            this.dispatcher = dispatcher;
        }

        @Override
        public Promise<FlowContext> apply(final FlowContext context) {
            List<Host> hosts = context.get(TopologyTasks.HOSTS);

            if (hosts != null && !hosts.isEmpty()) {
                List<Task<FlowContext>> tasks = hosts.stream()
                        .filter(host -> host.isAlive() && !host.isStarting() && host.isRunning()) // alive is not enough here!
                        .map(host -> {
                            ResourceAddress address = host.getAddress().add(CORE_SERVICE, PATCHING);
                            Operation operation = new Operation.Builder(address, READ_RESOURCE_OPERATION)
                                    .param(INCLUDE_RUNTIME, true)
                                    .param(RECURSIVE, true)
                                    .build();
                            return (Task<FlowContext>) c -> dispatcher.execute(operation)
                                    .then(result -> {
                                        host.get(CORE_SERVICE_PATCHING).set(result);
                                        return Promise.resolve(c);
                                    });
                        })
                        .collect(toList());
                return sequential(new FlowContext(Progress.NOOP), tasks).then(Promise::resolve);
            } else {
                return Promise.resolve(context);
            }
        }
    }

    private PatchTasks() {
    }
}
