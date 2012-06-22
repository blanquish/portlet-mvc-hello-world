<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="portletActionURL" scope="request"><portlet:actionURL escapeXml="false"/></c:set>

<form:form method="post" action="${portletActionURL}" commandName="configuration">

  <h3>Edit mode</h3>
  <p>This is the edit mode of the Hello World portlet</p>

  <label class="portlet-form-label text">Personalise your message:</label>
  <form:input path="personalisedMessage" cssClass="portlet-form-input-field select"/>

  <input type="submit" value="Save" class="portlet-form-button"/>

</form:form>