/*
 * Copyright 2015-2016 Red Hat, Inc, and individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.hal.core.extension;

import java.util.HashSet;
import java.util.Set;
import javax.inject.Inject;

import com.google.web.bindery.event.shared.EventBus;
import elemental.client.Browser;
import elemental.dom.Element;
import elemental.events.Event;
import jsinterop.annotations.JsIgnore;
import jsinterop.annotations.JsType;
import org.jboss.gwt.elemento.core.Elements;
import org.jboss.hal.core.ApplicationReadyEvent;
import org.jboss.hal.core.ApplicationReadyEvent.ApplicationReadyHandler;
import org.jboss.hal.core.extension.Extension.Kind;
import org.jboss.hal.resources.Ids;
import org.jetbrains.annotations.NonNls;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.jboss.gwt.elemento.core.EventType.click;
import static org.jboss.hal.resources.CSS.clickable;
import static org.jboss.hal.resources.CSS.hidden;

/**
 * @author Harald Pehl
 */
@JsType
public class ExtensionRegistry implements ApplicationReadyHandler {

    @NonNls private static final Logger logger = LoggerFactory.getLogger(ExtensionRegistry.class);

    private final Set<String> extensions;
    private boolean ready;
    private Element headerExtensions;
    private Element footerExtensions;

    @Inject
    public ExtensionRegistry(final EventBus eventBus) {
        extensions = new HashSet<>();
        eventBus.addHandler(ApplicationReadyEvent.getType(), this);
    }

    public void register(final Extension extension) {
        if (!ready) {
            logger.error("Cannot register extension {}: Application not ready", extension.id);
            return;
        }
        if (extensions.contains(extension.id)) {
            logger.error("Extension {} already registered", extension.id);
            return;
        }

        if (extension.kind == Kind.HEADER || extension.kind == Kind.FOOTER) {
            // @formatter:off
            Element li = new Elements.Builder()
                .li()
                    .a()
                        .id(extension.id)
                        .css(clickable)
                        .textContent(extension.title)
                        .on(click, event -> extension.entryPoint.execute())
                    .end()
                .end()
            .build();
            // @formatter:on

            Element ul;
            Element dropdown;
            if (extension.kind == Kind.HEADER) {
                dropdown = Browser.getDocument().getElementById(Ids.HEADER_EXTENSIONS_DROPDOWN);
                ul = headerExtensions;
            } else {
                dropdown = Browser.getDocument().getElementById(Ids.FOOTER_EXTENSIONS_DROPDOWN);
                ul = footerExtensions;
            }
            ul.appendChild(li);
            dropdown.getClassList().remove(hidden);
        }
        extensions.add(extension.id);
    }

    @Override
    @JsIgnore
    @SuppressWarnings("HardCodedStringLiteral")
    public void onApplicationReady(final ApplicationReadyEvent event) {
        ready = true;
        headerExtensions = Browser.getDocument().getElementById(Ids.HEADER_EXTENSIONS);
        footerExtensions = Browser.getDocument().getElementById(Ids.FOOTER_EXTENSIONS);

        // to be consumed by extension developers
        Event e = Browser.getDocument().createEvent("Event");
        e.initEvent("hal-extension-registry", true, true);
        Browser.getWindow().dispatchEvent(e);

        // TODO Load and inject extensions from management model
    }
}
