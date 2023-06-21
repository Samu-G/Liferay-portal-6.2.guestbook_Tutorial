<%@include file = "/html/guestbooklistcontroller/init.jsp" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>

<portlet:defineObjects />

<%@ taglib uri="http://alloy.liferay.com/tld/aui" prefix="aui"%>

<portlet:renderURL var="viewURL">
	<portlet:param name="mvcPath" value="/html/guestbooklistcontroller/view.jsp"></portlet:param>
</portlet:renderURL>

<%
	long entryId = ParamUtil.getLong(renderRequest, "entryId");

	Entry entry = null;

	if (entryId > 0) {

		entry = EntryLocalServiceUtil.getEntry(entryId);

	}

	long guestbookId = ParamUtil.getLong(renderRequest, "guestbookId");
%>

<portlet:actionURL name="addEntry" var="addEntryURL"></portlet:actionURL>

<aui:form action="<%=addEntryURL%>" name="<portlet:namespace />fm">

<aui:fieldset>

    <aui:input name="name" />
    <aui:input name="email" />
    <aui:input name="message" />
    <aui:input name='guestbookId' type='hidden' 
        value='<%= ParamUtil.getString(renderRequest, "guestbookId") %>'/>

</aui:fieldset>
	
	<aui:button-row>
		<aui:button type="submit"></aui:button>
		<aui:button type="cancel" onClick="<%=viewURL.toString()%>"></aui:button>
	</aui:button-row>

	<aui:model-context bean="<%= entry %>" model="<%= Entry.class %>" />
	
	<aui:input name="entryId" type="hidden" />
	<aui:input name="guestbookId" type="hidden" value='<%= entry == null ? guestbookId : entry.getGuestbookId() %>'/>

</aui:form>

