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
package org.jboss.hal.client.accesscontrol;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import org.jboss.hal.config.Role;
import org.jboss.hal.core.OperationFactory;
import org.jboss.hal.dmr.Composite;
import org.jboss.hal.dmr.ModelNode;
import org.jboss.hal.dmr.Operation;
import org.jboss.hal.dmr.ResourceAddress;
import org.jboss.hal.dmr.dispatch.Dispatcher;
import org.jboss.hal.flow.FlowContext;
import org.jboss.hal.flow.Task;
import org.jboss.hal.meta.Metadata;

import elemental2.promise.Promise;

import static java.util.stream.Collectors.toList;
import static org.jboss.hal.dmr.ModelDescriptionConstants.ADD;
import static org.jboss.hal.dmr.ModelDescriptionConstants.EXCLUDE;
import static org.jboss.hal.dmr.ModelDescriptionConstants.INCLUDE;
import static org.jboss.hal.dmr.ModelDescriptionConstants.INCLUDE_ALL;
import static org.jboss.hal.dmr.ModelDescriptionConstants.NAME;
import static org.jboss.hal.dmr.ModelDescriptionConstants.READ_RESOURCE_OPERATION;
import static org.jboss.hal.dmr.ModelDescriptionConstants.REALM;
import static org.jboss.hal.dmr.ModelDescriptionConstants.REMOVE;
import static org.jboss.hal.dmr.ModelDescriptionConstants.TYPE;
import static org.jboss.hal.dmr.ModelDescriptionConstants.VALUE;
import static org.jboss.hal.dmr.ModelDescriptionConstants.WRITE_ATTRIBUTE_OPERATION;

/** Tasks related to principals, roles and assignments. */
final class AccessControlTasks {

    /**
     * Checks whether a role mapping for a given role exists and pushes {@code 200} to the context stack if it exists,
     * {@code 404} otherwise.
     */
    // We cannot inherit from org.jboss.hal.dmr.ResourceCheck since the
    // Task<FlowContext> implementations have to be final (due to @JsFunction)
    static final class CheckRoleMapping implements Task<FlowContext> {

        private final Dispatcher dispatcher;
        private final ResourceAddress address;

        CheckRoleMapping(Dispatcher dispatcher, Role role) {
            this.dispatcher = dispatcher;
            this.address = AddressTemplates.roleMapping(role);
        }

        @Override
        public Promise<FlowContext> apply(final FlowContext context) {
            Operation operation = new Operation.Builder(address, READ_RESOURCE_OPERATION).build();
            return dispatcher.execute(operation)
                    .then(result -> context.resolve(200))
                    .catch_(error -> context.resolve(404));
        }
    }

    /**
     * Adds a role mapping for a given role if the predicate returns {@code true}, proceeds otherwise. Expects an integer status
     * code at the top of the context stack which is used to call the predicate.
     */
    static final class AddRoleMapping implements Task<FlowContext> {

        private final Dispatcher dispatcher;
        private final Role role;
        private final Predicate<Integer> predicate;

        AddRoleMapping(Dispatcher dispatcher, Role role, Predicate<Integer> predicate) {
            this.dispatcher = dispatcher;
            this.role = role;
            this.predicate = predicate;
        }

        @Override
        public Promise<FlowContext> apply(final FlowContext context) {
            if (!context.emptyStack()) {
                Integer status = context.pop();
                if (predicate.test(status)) {
                    Operation operation = new Operation.Builder(AddressTemplates.roleMapping(role), ADD).build();
                    return dispatcher.execute(operation).then(__ -> Promise.resolve(context));
                } else {
                    return Promise.resolve(context);
                }
            } else {
                return Promise.resolve(context);
            }
        }
    }

    /**
     * Modifies the include-all flag of a role-mapping. Please make sure that the role-mapping exists before using this
     * function. Use a combination of {@link CheckRoleMapping} and {@link AddRoleMapping} to do so.
     */
    static final class ModifyIncludeAll implements Task<FlowContext> {

        private final Dispatcher dispatcher;
        private final Role role;
        private final boolean includeAll;

        ModifyIncludeAll(Dispatcher dispatcher, Role role, boolean includeAll) {
            this.dispatcher = dispatcher;
            this.role = role;
            this.includeAll = includeAll;
        }

        @Override
        public Promise<FlowContext> apply(final FlowContext context) {
            Operation operation = new Operation.Builder(AddressTemplates.roleMapping(role), WRITE_ATTRIBUTE_OPERATION)
                    .param(NAME, INCLUDE_ALL)
                    .param(VALUE, includeAll)
                    .build();
            return dispatcher.execute(operation).then(__ -> Promise.resolve(context));
        }
    }

    /**
     * Removes a role mapping for a given role if the predicate returns {@code true}, proceeds otherwise. Expects an integer
     * status code at the top of the context stack which is used to call the predicate.
     */
    static final class RemoveRoleMapping implements Task<FlowContext> {

        private final Dispatcher dispatcher;
        private final Role role;
        private final Predicate<Integer> predicate;

        RemoveRoleMapping(Dispatcher dispatcher, Role role, Predicate<Integer> predicate) {
            this.dispatcher = dispatcher;
            this.role = role;
            this.predicate = predicate;
        }

        @Override
        public Promise<FlowContext> apply(final FlowContext context) {
            if (!context.emptyStack()) {
                Integer status = context.pop();
                if (predicate.test(status)) {
                    Operation operation = new Operation.Builder(AddressTemplates.roleMapping(role), REMOVE).build();
                    return dispatcher.execute(operation).then(__ -> Promise.resolve(context));
                } else {
                    return Promise.resolve(context);
                }
            } else {
                return Promise.resolve(context);
            }
        }
    }

    /**
     * Adds an assignment to a role-mapping. Please make sure that the role-mapping exists before using this task. Use a
     * combination of {@link CheckRoleMapping} and {@link AddRoleMapping} to do so.
     */
    static final class AddAssignment implements Task<FlowContext> {

        private final Dispatcher dispatcher;
        private final Role role;
        private final Principal principal;
        private final boolean include;

        AddAssignment(Dispatcher dispatcher, Role role, Principal principal, boolean include) {
            this.dispatcher = dispatcher;
            this.role = role;
            this.principal = principal;
            this.include = include;
        }

        @Override
        public Promise<FlowContext> apply(final FlowContext context) {
            ResourceAddress address = AddressTemplates.roleMapping(role)
                    .add(include ? INCLUDE : EXCLUDE, principal.getResourceName());
            Operation.Builder builder = new Operation.Builder(address, ADD)
                    .param(NAME, principal.getName())
                    .param(TYPE, principal.getType().name());
            if (principal.getRealm() != null) {
                builder.param(REALM, principal.getRealm());
            }
            return dispatcher.execute(builder.build()).then(__ -> Promise.resolve(context));
        }
    }

    /**
     * Removes assignments from a role-mapping. Please make sure that the role-mapping exists before using this function. Use a
     * combination of {@link CheckRoleMapping} and {@link AddRoleMapping} to do so.
     */
    static final class RemoveAssignments implements Task<FlowContext> {

        private final Dispatcher dispatcher;
        private final List<Assignment> assignments;

        RemoveAssignments(Dispatcher dispatcher, List<Assignment> assignments) {
            this.dispatcher = dispatcher;
            this.assignments = assignments;
        }

        @Override
        public Promise<FlowContext> apply(final FlowContext context) {
            if (assignments.isEmpty()) {
                return Promise.resolve(context);
            } else if (assignments.size() == 1) {
                Assignment assignment = assignments.get(0);
                ResourceAddress address = AddressTemplates.assignment(assignment);
                Operation operation = new Operation.Builder(address, REMOVE).build();
                return dispatcher.execute(operation).then(__ -> Promise.resolve(context));
            } else {
                List<Operation> operations = assignments.stream()
                        .map(assignment -> {
                            ResourceAddress address = AddressTemplates.assignment(assignment);
                            return new Operation.Builder(address, REMOVE).build();
                        })
                        .collect(toList());
                return dispatcher.execute(new Composite(operations)).then(__ -> Promise.resolve(context));
            }
        }
    }

    /** Adds a scoped role. */
    static final class AddScopedRole implements Task<FlowContext> {

        private final Dispatcher dispatcher;
        private final Role.Type type;
        private final String name;
        private final ModelNode payload;

        AddScopedRole(Dispatcher dispatcher, Role.Type type, String name, ModelNode payload) {
            this.dispatcher = dispatcher;
            this.type = type;
            this.name = name;
            this.payload = payload;
        }

        @Override
        public Promise<FlowContext> apply(final FlowContext context) {
            ResourceAddress address = AddressTemplates.scopedRole(new Role(name, null, type, null));
            Operation operation = new Operation.Builder(address, ADD)
                    .payload(payload)
                    .build();
            return dispatcher.execute(operation).then(__ -> Promise.resolve(context));
        }
    }

    /** Modifies a scoped role. */
    static final class ModifyScopedRole implements Task<FlowContext> {

        private final Dispatcher dispatcher;
        private final Role role;
        private final Map<String, Object> changedValues;
        private final Metadata metadata;

        ModifyScopedRole(Dispatcher dispatcher, Role role, Map<String, Object> changedValues,
                Metadata metadata) {
            this.dispatcher = dispatcher;
            this.role = role;
            this.changedValues = changedValues;
            this.metadata = metadata;
        }

        @Override
        public Promise<FlowContext> apply(final FlowContext context) {
            ResourceAddress address = AddressTemplates.scopedRole(role);
            Operation operation = new OperationFactory().fromChangeSet(address, changedValues, metadata);
            return dispatcher.execute(operation).then(__ -> Promise.resolve(context));
        }
    }

    /**
     * Removes a scoped role.
     */
    static final class RemoveScopedRole implements Task<FlowContext> {

        private final Dispatcher dispatcher;
        private final Role role;

        RemoveScopedRole(Dispatcher dispatcher, Role role) {
            this.dispatcher = dispatcher;
            this.role = role;
        }

        @Override
        public Promise<FlowContext> apply(final FlowContext context) {
            ResourceAddress address = AddressTemplates.scopedRole(role);
            Operation operation = new Operation.Builder(address, REMOVE).build();
            return dispatcher.execute(operation).then(__ -> Promise.resolve(context));
        }
    }

    private AccessControlTasks() {
    }
}
