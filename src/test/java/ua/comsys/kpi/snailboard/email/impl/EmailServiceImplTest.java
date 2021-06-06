package ua.comsys.kpi.snailboard.email.impl;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import ua.comsys.kpi.snailboard.email.exception.CannotSendEmailException;

import javax.mail.MessagingException;
import java.util.HashMap;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
class EmailServiceImplTest {

    private static final String SNAILBOARD_EMAIL = "SNAILBOARD_EMAIL";
    private static final String SNAILBOARD_EMAIL_VALUE = "test";
    private static final String SNAILBOARD_PASSWORD = "SNAILBOARD_PASSWORD";
    private static final String SNAILBOARD_PASSWORD_VALUE = "test";
    private static final String PROP_KEY_1 = "prop1";
    private static final String PROP_VALUE_1 = "rost";
    private static final String PROP_KEY_2 = "prop2";
    private static final String PROP_VALUE_2 = "pes";
    private static final String FILENAME = "test.html";
    private static final String SUBJECT = "subject";
    private static final String HTML_TEXT = "text/html";
    private static final String TEMPLATE_TEXT = "<h1>rost is pes</h1>";


    @InjectMocks
    EmailServiceImpl testingInstance = new EmailServiceImpl();

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(testingInstance, SNAILBOARD_EMAIL, SNAILBOARD_EMAIL_VALUE);
        ReflectionTestUtils.setField(testingInstance, SNAILBOARD_PASSWORD, SNAILBOARD_PASSWORD_VALUE);
    }

    @Test
    public void shouldCreateTemplate() {
        Map<String, String> props = new HashMap<>();
        props.put(PROP_KEY_1, PROP_VALUE_1);
        props.put(PROP_KEY_2, PROP_VALUE_2);

        Assert.assertThrows(CannotSendEmailException.class,
                () -> testingInstance.sendEmail("test@email.com", props, FILENAME, SUBJECT));

        assertThat(testingInstance.getTemplate(), is(TEMPLATE_TEXT));
    }

}