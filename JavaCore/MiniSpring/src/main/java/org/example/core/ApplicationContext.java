package org.example.core;

import org.example.annotations.Autowired;
import org.example.annotations.Component;
import org.example.annotations.Scope;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.*;

public class ApplicationContext {

    private final Map<Class<?>, Object> singletonBeans = new HashMap<>();
    private final Map<Class<?>, String> beanDefinitions = new HashMap<>();
    private final String basePackage;

    public ApplicationContext(String basePackage) {
        this.basePackage = basePackage;
        scanAndInitializeBeans();
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> type) {
        Object bean = singletonBeans.get(type);

        if (bean == null && beanDefinitions.containsKey(type)) {
            return createBeanInstance(type);
        }

        return (T) bean;
    }

    private void scanAndInitializeBeans() {
        try {
            List<Class<?>> classes = findClassesInPackage(basePackage);

            for (Class<?> clazz : classes) {
                if (clazz.isAnnotationPresent(Component.class)) {
                    beanDefinitions.put(clazz, getScope(clazz));

                    if ("singleton".equals(getScope(clazz))) {
                        Object bean = createBeanInstance(clazz);
                        singletonBeans.put(clazz, bean);
                    }
                }
            }

            for (Object bean : singletonBeans.values()) {
                injectDependencies(bean);
            }

            for (Object bean : singletonBeans.values()) {
                invokeInitMethod(bean);
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize application context", e);
        }
    }

    private <T> T createBeanInstance(Class<T> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create bean instance: " + clazz.getName(), e);
        }
    }

    private void injectDependencies(Object bean) throws IllegalAccessException {
        Class<?> clazz = bean.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(Autowired.class)) {
                field.setAccessible(true);
                Class<?> fieldType = field.getType();

                Object dependency = getBean(fieldType);
                if (dependency == null) {
                    throw new RuntimeException("No bean found for type: " + fieldType.getName());
                }

                field.set(bean, dependency);
            }
        }
    }

    private void invokeInitMethod(Object bean) throws Exception {
        if (bean instanceof InitializingBean) {
            ((InitializingBean) bean).afterPropertiesSet();
        }
    }

    private String getScope(Class<?> clazz) {
        if (clazz.isAnnotationPresent(Scope.class)) {
            return clazz.getAnnotation(Scope.class).value();
        }
        return "singleton";
    }

    private List<Class<?>> findClassesInPackage(String packageName) {
        List<Class<?>> classes = new ArrayList<>();
        String path = packageName.replace('.', '/');

        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            Enumeration<URL> resources = classLoader.getResources(path);

            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                if (resource.getProtocol().equals("file")) {
                    classes.addAll(findClasses(new File(resource.getFile()), packageName));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to scan package: " + packageName, e);
        }

        return classes;
    }

    private List<Class<?>> findClasses(File directory, String packageName) {
        List<Class<?>> classes = new ArrayList<>();

        if (!directory.exists()) {
            return classes;
        }

        File[] files = directory.listFiles();
        if (files == null) return classes;

        for (File file : files) {
            if (file.isDirectory()) {
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                String className = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
                try {
                    classes.add(Class.forName(className));
                } catch (ClassNotFoundException e) {
                    System.err.println("Class not found: " + className);
                }
            }
        }

        return classes;
    }

}
