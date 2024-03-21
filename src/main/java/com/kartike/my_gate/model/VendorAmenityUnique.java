package com.kartike.my_gate.model;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import com.kartike.my_gate.service.VendorServiceImpl;
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
import java.util.UUID;
import org.springframework.web.servlet.HandlerMapping;


/**
 * Validate that the id value isn't taken yet.
 */
@Target({ FIELD, METHOD, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = VendorAmenityUnique.VendorAmenityUniqueValidator.class
)
public @interface VendorAmenityUnique {

    String message() default "{Exists.vendor.amenity}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class VendorAmenityUniqueValidator implements ConstraintValidator<VendorAmenityUnique, Long> {

        private final VendorServiceImpl vendorService;
        private final HttpServletRequest request;

        public VendorAmenityUniqueValidator(final VendorServiceImpl vendorService,
                final HttpServletRequest request) {
            this.vendorService = vendorService;
            this.request = request;
        }

        @Override
        public boolean isValid(final Long value, final ConstraintValidatorContext cvContext) {
            if (value == null) {
                // no value present
                return true;
            }
            @SuppressWarnings("unchecked") final Map<String, String> pathVariables =
                    ((Map<String, String>)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE));
            final String currentId = pathVariables.get("vendorId");

            return !vendorService.amenityExists(value);
        }

    }

}
