package ru.rmntim.web.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.Arrays;
import java.util.List;

@FacesValidator("xValidator")
public class XValidator implements Validator {

    private static final List<Double> VALID_VALUES = Arrays.asList(
            -2.0, -1.5, -1.0, -0.5, 0.0, 0.5, 1.0, 1.5
    );

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) {
        System.out.println("[XValidator] Проверка значения: " + value);

        if (value == null || value.toString().trim().isEmpty()) {
            throw new ValidatorException(
                    new FacesMessage("X не может быть пустым"));
        }

        try {
            double xValue = Double.parseDouble(value.toString().trim());

            if (!VALID_VALUES.contains(xValue)) {
                throw new ValidatorException(
                        new FacesMessage("X должен быть одним из: -2.0, -1.5, -1.0, -0.5, 0.0, 0.5, 1.0, 1.5"));
            }

        } catch (NumberFormatException e) {
            throw new ValidatorException(
                    new FacesMessage("X должен быть числом"));
        }
    }
}