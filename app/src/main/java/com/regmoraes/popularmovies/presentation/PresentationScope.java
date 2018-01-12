package com.regmoraes.popularmovies.presentation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Copyright {2018} {Rômulo Eduardo G. Moraes}
 **/
@Scope
@Documented
@Retention(value = RetentionPolicy.RUNTIME)
public @interface PresentationScope {}
