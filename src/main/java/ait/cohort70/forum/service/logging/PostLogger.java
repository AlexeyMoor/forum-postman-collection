package ait.cohort70.forum.service.logging;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) // Эта аннотация будет доступна во время выполнения
@Target({ElementType.METHOD}) // Этот аннотация для методов
public @interface PostLogger {
}
