package ru.rmntim.web.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("rValidator")
public class RValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) {
        System.out.println("[RValidator] Проверка значения: " + value);

        if (value == null || value.toString().trim().isEmpty()) {
            throw new ValidatorException(
                    new FacesMessage("R не может быть пустым"));
        }

        try {
            double rValue = Double.parseDouble(value.toString().trim());

            if (rValue < 1 || rValue > 5) {
                throw new ValidatorException(
                        new FacesMessage("R должен быть от 1 до 5"));
            }

            // Проверка на целое число от 1 до 5
            if (rValue != Math.floor(rValue)) {
                throw new ValidatorException(
                        new FacesMessage("R должен быть целым числом (1, 2, 3, 4, 5)"));
            }

        } catch (NumberFormatException e) {
            throw new ValidatorException(
                    new FacesMessage("R должен быть числом"));
        }
    }
}