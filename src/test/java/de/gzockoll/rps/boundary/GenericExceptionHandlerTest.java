package de.gzockoll.rps.boundary;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.SecureRandom;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GenericExceptionHandlerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private HttpServletRequest req;

    @Mock
    private IllegalArgumentException iax;

    @Mock
    private IllegalStateException isx;

    @InjectMocks
    private GenericExceptionHandler handler;

    private SecureRandom random = new SecureRandom();

    public String aRandomString() {
        return new BigInteger(130, random).toString(32);
    }

    @Test
    public void handleIllegalArgumentException() throws Exception {
        String message = aRandomString();
        when(iax.getLocalizedMessage()).thenReturn(message);
        ModelAndView mav = handler.handleIllegalArgumentException(req, iax);
        assertThat(mav.getModel()).hasSize(2);
        assertThat(mav.getModel().get("message")).isEqualTo(message);
        assertThat(mav.getModel().get("exception")).isSameAs(iax);

    }

    @Test
    public void handleIllegalStateException() throws Exception {
        String message = aRandomString();
        when(isx.getLocalizedMessage()).thenReturn(message);
        ModelAndView mav = handler.handleIllegalStateException(req, isx);
        assertThat(mav.getModel()).hasSize(2);
        assertThat(mav.getModel().get("message")).isEqualTo(message);
        assertThat(mav.getModel().get("exception")).isSameAs(isx);

    }

    @Test
    public void testAnnotatedIllegalArgumentException() {
        thrown.expect(TestException1.class);
        handler.handleIllegalArgumentException(req, new TestException1());
    }

    @Test
    public void testAnnotatedIllegalStateException() {
        thrown.expect(TestException2.class);
        handler.handleIllegalStateException(req, new TestException2());
    }


    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    public static class TestException1 extends IllegalArgumentException {

    }

    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    public static class TestException2 extends IllegalStateException {

    }
}