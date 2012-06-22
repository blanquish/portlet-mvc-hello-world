package helloWorld;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.ReadOnlyException;

/**
 * Configuration for the Hello World portlet
 *
 * @author Blanca Garcia
 */
public class Configuration {

    private boolean isInitialized = false;
    public static final String MODEL_ATT_NAME = "configuration";

    private Map<String, List<String>> configurationPreferences = new HashMap<String,List<String>>();

    public static final String MESSAGE = "personalisedMessage";
    private static final String DEFAULT_MESSAGE = "Hello World";


    public String getPersonalisedMessage() {
        return this.getString(MESSAGE, DEFAULT_MESSAGE);
    }

    public void setPersonalisedMessage(String message) {
        this.setString(MESSAGE, message);
    }

    /*
        The code below is borrowed from the {@link AbstractPortletConfiguration} class
     */

    /**
     * Load this Configuration from the portlet preferences.
     *
     * @param request the current portlet request
     * @return a instance of this object with its values loaded from the portlet preferences
     */
    public static Configuration getConfiguration(PortletRequest request) {
        Configuration configuration = new Configuration();
        PortletPreferences portletPreferences = request.getPreferences();
        configuration.readPreferences(portletPreferences);
        return configuration;
    }

    private void readPreferences(PortletPreferences preferences) {
        for (Object key : preferences.getMap().keySet()) {
            configurationPreferences.put((String) key, Arrays.asList(preferences.getValues((String) key, null)));
        }
        isInitialized = true;
    }

    private String getString(String key, String defaultValue){
        List<String> values = getStrings(key, new ArrayList<String>(0));
        if( values.size() > 0 ){
            return values.get(0);
        } else {
            return defaultValue;
        }
    }

    private List<String> getStrings(String key, List<String> defaultValues){
        if( configurationPreferences.containsKey(key) ){
            return configurationPreferences.get(key);
        } else {
            return defaultValues;
        }
    }

    private void setString(String key, String value) {
        setStrings(key, Collections.singletonList(value));
    }

    private void setStrings(String key, List<String> values){
        configurationPreferences.put(key, values);
    }

    /**
     * @param preferences the preferences to write to
     */
    public void writePreferences(PortletPreferences preferences){
        if(!isInitialized){
            throw new IllegalStateException("Configuration has not been initialized yet.");
        }
        try {
            for(String key : configurationPreferences.keySet()){
                List<String> value = configurationPreferences.get(key);
                preferences.setValues(key, value.toArray(new String[value.size()]));
            }
        } catch (ReadOnlyException roe) {
            throw new IllegalStateException(roe);
        }
    }


}
