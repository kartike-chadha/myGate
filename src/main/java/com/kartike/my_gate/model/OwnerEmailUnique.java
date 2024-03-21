package com.kartike.my_gate.model;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import com.kartike.my_gate.service.OwnerServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;
import org.springframework.web.servlet.HandlerMapping;


/**
 * Validate that the email value isn't taken yet.
 */
@Target({ FIELD, METHOD, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = OwnerEmailUnique.OwnerEmailUniqueValidator.class
)
public @interface OwnerEmailUnique {

    String message() default "{Exists.owner.email}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class OwnerEmailUniqueValidator implements ConstraintValidator<OwnerEmailUnique, String> {

        private final OwnerServiceImpl ownerService;
        private final HttpServletRequest request;

        public OwnerEmailUniqueValidator(final OwnerServiceImpl ownerService,
                final HttpServletRequest request) {
            this.ownerService = ownerService;
            this.request = request;
        }

        @Override
        public boolean isValid(final String value, final ConstraintValidatorContext cvContext) {
            if (value == null) {
                // no value present
                return true;
            }
            @SuppressWarnings("unchecked") final Map<String, String> pathVariables =
                    ((Map<String, String>)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE));
            final String currentId = pathVariables.get("id");
            if (currentId != null && value.equalsIgnoreCase(ownerService.get(Integer.parseInt(currentId)).getEmail())) {
                // value hasn't changed
                return true;
            }
            return !ownerService.emailExists(value);
        }

    }

}
