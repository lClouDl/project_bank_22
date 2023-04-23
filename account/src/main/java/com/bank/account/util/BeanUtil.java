package com.bank.account.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Класс-компонент, метод которого позволяет нам правильно создать Been для {@link javax.persistence.EntityManager} в любом месте
 * реализует интерфейс {@link ApplicationContextAware}
 * @see <a href="Узнал о такой реализации тут: ">https://stackoverflow.com/questions/22171221/how-to-inject-entitymanager-in-entitylisteners#:~:text=11-,I,-have%20faced%20a</a>
 */
@Component
public class BeanUtil implements ApplicationContextAware {

    private static ApplicationContext context;

    /**
     * Метод, который позволяет установить объект класса {@link ApplicationContext}
     * @param applicationContext объект класса {@link ApplicationContext}, который требуется сохранить в классе {@link BeanUtil}
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    /**
     * Метод, который формирует been для переданного класса.
     * @param <T> дженерик переданного класса
     * @param beanClass класс, бин которого требуется создать
     * @return возвращает бин переданного класса
     */
    public static <T> T getBean(Class<T> beanClass) {
        return context.getBean(beanClass);
    }

}
