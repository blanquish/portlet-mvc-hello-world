package helloWorld;

import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.RenderResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * View controller for the Hello World portlet
 *
 * @author Blanca Garcia
 */
@Controller
@RequestMapping("VIEW")
public class ViewController {

    private static final Log log = LogFactory.getLog(ViewController.class);
    private static final String VIEW_NAME = "helloWorld";

    /**
     * Controls rendering the portlet. Fetches de XSL selected and displays it
     *
     * @param model    the Model which all attributes required in the view are added to
     * @param request  the current portlet request
     * @param response the current portlet response
     * @return the name of the jsp which handles the view, {@link #VIEW_NAME}
     * @throws PortletException is case something goes wrong.
     */
    @RequestMapping
    public String view(ModelMap model, PortletRequest request, RenderResponse response) throws PortletException {

        Configuration configuration = Configuration.getConfiguration(request);
        model.put("helloWorldMessage", configuration.getPersonalisedMessage());

        return VIEW_NAME;
    }
}
