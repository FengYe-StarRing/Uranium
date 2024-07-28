package com.github.fengye.starring.uranium.manager.impl;

import com.github.fengye.starring.uranium.api.event.Event;
import com.github.fengye.starring.uranium.api.event.EventHandle;
import com.github.fengye.starring.uranium.api.event.Listenable;
import com.github.fengye.starring.uranium.api.event.impl.motion.MotionEvent;
import com.github.fengye.starring.uranium.listenable.special.Palette;
import com.github.fengye.starring.uranium.manager.Manager;
import com.github.fengye.starring.uranium.utils.misc.JavaUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class EventManager extends Manager {
    private final List<HandleMethod> handleMethods = new ArrayList<>();

    public EventManager() {
        super("EventManager");
    }

    @Override
    public void init() {
        super.init();
        handleMethods.clear();

        registerListener(new Palette());
    }

    public void registerListener(Listenable listenable) {
        List<Method> methods = JavaUtils.getDeclaredMethods(JavaUtils.getClassLoader(listenable));
        for (Method method : methods) {
            if (method.isAnnotationPresent(EventHandle.class) && method.getParameterTypes().length == 1) {
                method.setAccessible(true);
                Class<? extends Event> eventClass = (Class<? extends Event>) method.getParameterTypes()[0];
                EventHandle eventHandle = method.getAnnotation(EventHandle.class);
                handleMethods.add(new HandleMethod(listenable,eventClass,eventHandle,method));
            }
        }
        handleMethods.sort(Comparator.comparingInt(h -> h.getEventHandle().priority()));
    }

    public Event callEvent(Event event) {
        for (HandleMethod handleMethod : handleMethods) {
            Listenable l = handleMethod.getListenable();
            if(!l.handleEvents()) {
                continue;
            }
            Class<?> eventClass = handleMethod.getEventClass();
            if(eventClass != JavaUtils.getClassLoader(event) && eventClass != Event.class && eventClass != event.getaClass()) {
                continue;
            }
//            try {
//                handleMethod.getMethod().invoke(l,event);
//            } catch (IllegalAccessException | InvocationTargetException ignored) {}
            try {
                handleMethod.getMethod().invoke(l,event);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
        return event;
    }

    public List<HandleMethod> getHandleMethods() {
        return handleMethods;
    }

    public static class HandleMethod {
        private final Listenable listenable;
        private final Class<? extends Event> eventClass;
        private final EventHandle eventHandle;
        private final Method method;

        public HandleMethod(Listenable l,Class<? extends Event> eventClass,EventHandle eventHandle,Method method) {
            listenable = l;
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

        public Listenable getListenable() {
            return listenable;
        }
    }
}
