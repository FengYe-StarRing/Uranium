package com.github.fengye.starring.uranium.manager.impl;

import com.github.fengye.starring.uranium.api.event.Event;
import com.github.fengye.starring.uranium.api.event.EventHandle;
import com.github.fengye.starring.uranium.api.event.Listenable;
import com.github.fengye.starring.uranium.manager.Manager;
import com.github.fengye.starring.uranium.utils.misc.JavaUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class EventManager extends Manager {
    private final List<HandleClass> handleClasses = new ArrayList<>();

    public EventManager() {
        super("EventManager");
    }

    @Override
    public void init() {
        super.init();
        handleClasses.clear();
    }

    public void registerListener(Listenable listenable) {
        List<Method> methods = JavaUtils.getDeclaredMethods(JavaUtils.getClassLoader(listenable));
        List<HandleMethod> handleMethods = new ArrayList<>();
        for (Method method : methods) {
            if (method.isAnnotationPresent(EventHandle.class) && method.getParameterTypes().length == 1) {
                if (!method.isAccessible()) {
                    method.setAccessible(true);
                }
                Class<? extends Event> eventClass = (Class<? extends Event>) method.getParameterTypes()[0];
                EventHandle eventHandle = method.getAnnotation(EventHandle.class);
                handleMethods.add(new HandleMethod(eventClass,eventHandle,method));
            }
        }
        handleMethods.sort(Comparator.comparingInt(h -> h.getEventHandle().priority()));
        handleClasses.add(new HandleClass(listenable,handleMethods));
    }

    public Event callEvent(Event event) {
        for (HandleClass handleClass : handleClasses) {
            Listenable listenable = handleClass.getListenable();
            if(!listenable.handleEvents()) {
                continue;
            }
            for (HandleMethod method : handleClass.getMethods()) {
                if(method.getEventClass() != JavaUtils.getClassLoader(event)) {
                    continue;
                }
                try {
                    method.getMethod().invoke(listenable,event);
                } catch (IllegalAccessException | InvocationTargetException ignored) {}
            }
        }
        return event;
    }

    public List<HandleClass> getHandleClasses() {
        return handleClasses;
    }

    public static class HandleClass {
        private final Listenable listenable;
        private final List<HandleMethod> methods;

        public HandleClass(Listenable listenable,List<HandleMethod> methods) {
            this.listenable = listenable;
            this.methods = methods;
        }

        public Listenable getListenable() {
            return listenable;
        }

        public List<HandleMethod> getMethods() {
            return methods;
        }
    }

    public static class HandleMethod {
        private final Class<? extends Event> eventClass;
        private final EventHandle eventHandle;
        private final Method method;

        public HandleMethod(Class<? extends Event> eventClass,EventHandle eventHandle,Method method) {
            this.eventClass = eventClass;
            this.eventHandle = eventHandle;
            this.method = method;
        }

        public Class<? extends Event> getEventClass() {
            return eventClass;
        }

        public EventHandle getEventHandle() {
            return eventHandle;
        }

        public Method getMethod() {
            return method;
        }
    }
}
