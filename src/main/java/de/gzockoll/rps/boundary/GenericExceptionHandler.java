package de.gzockoll.rps.boundary;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Slf4j
class GenericExceptionHandler {
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(IllegalArgumentException.class)
    @SuppressWarnings("squid:S1172") // Unused method parameters should be removed
    public ModelAndView handleIllegalArgumentException(HttpServletRequest req, IllegalArgumentException ex) {
        rethrowExceptionIfResponseStatusIsSet(ex);
        return buildModelAndViewFromException(ex);
    }

    private ModelAndView buildModelAndViewFromException(Exception ex) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("message", ex.getLocalizedMessage());
        mv.addObject("exception", ex);
        mv.setViewName("error");
        return mv;
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(IllegalStateException.class)
    @SuppressWarnings("squid:S1172") // Unused method parameters should be removed
    public ModelAndView handleIllegalStateException(HttpServletRequest req, IllegalStateException ex) {
        rethrowExceptionIfResponseStatusIsSet(ex);
        return buildModelAndViewFromException(ex);
    }

    void rethrowExceptionIfResponseStatusIsSet(RuntimeException rex) {
        if (AnnotationUtils.findAnnotation(rex.getClass(), ResponseStatus.class) != null)
            throw rex;
    }
}
