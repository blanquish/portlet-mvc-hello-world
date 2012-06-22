package helloWorld;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.ValidatorException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import static helloWorld.Configuration.MODEL_ATT_NAME;

/**
 * Edit controller for the Hello World portlet
 *
 * @author Blanca Garcia
 */
@Controller
@RequestMapping("EDIT")
@SessionAttributes(MODEL_ATT_NAME)
public class EditController {

    private static final Log log = LogFactory.getLog(EditController.class);

    /**
     * The name of the JSP page which used for the display of the edit mode.
     */
    private static final String EDIT_JSP = "edit";
    private static final String NOT_SAVED = "notSaved";


    /**
     * Controller method for when a user views the edit mode of the portlet.
     * Provides a xsl list to choose from, and values for custom properties for
     * the portlet (show ordered files, show messages, show message if no user is logged in)
     *
     * @param model   the model which any attributes required in the view of the edit mode are added to
     * @param request the current portlet request
     * @return the name of the edit mode jsp.
     */
    @SuppressWarnings({"ProhibitedExceptionDeclared"})
    @RequestMapping
    public String viewEdit(ModelMap model, PortletRequest request) {

        Configuration configuration = getConfigureForRender(model, request);
        model.addAttribute(MODEL_ATT_NAME, configuration);

        return EDIT_JSP;
    }

    /**
     * Controller method for when the edit mode is submitted. Simply stores the Portlet Preferences.
     *
     * @param request the current portlet request
     * @param model the model for the current portlet request
     * @param configuration the command class of this portlet. This is a session attribute.
     * @param errors the object that any errors are added to
     * @throws ValidatorException
     *                             if there is a problem validating the input
     * @throws IOException if there is a problem saving the portlet preferences
     */
    @RequestMapping
    public void submitEdit(ActionRequest request, ModelMap model, @ModelAttribute(value = MODEL_ATT_NAME) Configuration configuration, Errors errors)
            throws ValidatorException, IOException {

        if (log.isDebugEnabled()) {
            log.debug("Request to save Config[" + configuration + "]");
        }

        // invoke validator to check all portlet properties
//        validator.validate(configuration, errors);

        // to make this compatible with the current portlets, do not save the configuration if there are any errors
        if (!errors.hasErrors()) {
            PortletPreferences preferences = request.getPreferences();
            configuration.writePreferences(preferences);
            preferences.store();
        } else {
            // do not save the configuration, but add this attribute so that the render phase knows it was not saved
            model.addAttribute(NOT_SAVED, true);
        }

    }
    /**
     * Get the ConfigureDocumentConfiguration for the render phase of the edit mode. It is also added to the Model if required.
     *
     * @param model   the model for the current portlet request
     * @param request the current portlet request
     * @return the ConfigureDocumentConfiguration that should be used in the render
     */
    private Configuration getConfigureForRender(ModelMap model, PortletRequest request) {
        if (model.get(NOT_SAVED) != null) {
            // the last request to this portlet was an ActionRequest and the were errors. Return the Config stored in the model
            return (Configuration) model.get(MODEL_ATT_NAME);
        } else {
            Configuration configuration = Configuration.getConfiguration(request);
            model.addAttribute(MODEL_ATT_NAME, configuration);
            return configuration;
        }
    }

}
