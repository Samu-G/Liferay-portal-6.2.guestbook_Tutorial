package com.liferay.docs.guestbook.portlet;


import com.liferay.docs.guestbook.model.Entry;
import com.liferay.docs.guestbook.model.Guestbook;
import com.liferay.docs.guestbook.service.EntryLocalServiceUtil;
import com.liferay.docs.guestbook.service.GuestbookLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

import java.io.IOException;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * Portlet implementation class GuestbookPortlet
 */
public class GuestbookListController extends MVCPortlet {
 
	public void addGuestbook(ActionRequest request, ActionResponse response) throws PortalException, SystemException {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(Guestbook.class.getName(), request);

		String name = ParamUtil.getString(request, "name");

		try {
			GuestbookLocalServiceUtil.addGuestbook(serviceContext.getUserId(), name, serviceContext);

			SessionMessages.add(request, "guestbookAdded");

		} catch (Exception e) {
			SessionErrors.add(request, e.getClass().getName());

			response.setRenderParameter("mvcPath", "/html/guestbook/edit_guestbook.jsp");
		}

	}

	public void addEntry(ActionRequest request, ActionResponse response) throws PortalException, SystemException {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(Entry.class.getName(), request);

		String userName = ParamUtil.getString(request, "name");
		String email = ParamUtil.getString(request, "email");
		String message = ParamUtil.getString(request, "message");
		long guestbookId = ParamUtil.getLong(request, "guestbookId");

		try {
			EntryLocalServiceUtil.addEntry(serviceContext.getUserId(), guestbookId, userName, email, message,
					serviceContext);

			SessionMessages.add(request, "entryAdded");

			response.setRenderParameter("guestbookId", Long.toString(guestbookId));

		} catch (Exception e) {
			SessionErrors.add(request, e.getClass().getName());

			PortalUtil.copyRequestParameters(request, response);

			response.setRenderParameter("mvcPath", "/html/guestbook/edit_entry.jsp");
		}

	}
	
	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse)
			throws PortletException, IOException {

		try {
			ServiceContext serviceContext = ServiceContextFactory.getInstance(Guestbook.class.getName(), renderRequest);

			long groupId = serviceContext.getScopeGroupId();

			long guestbookId = ParamUtil.getLong(renderRequest, "guestbookId");

			List<Guestbook> guestbooks = GuestbookLocalServiceUtil.getGuestbooks(groupId);

			if (guestbooks.size() == 0) {
				Guestbook guestbook = GuestbookLocalServiceUtil.addGuestbook(serviceContext.getUserId(), "Main",
						serviceContext);

				guestbookId = guestbook.getGuestbookId();

			}

			if (!(guestbookId > 0)) {
				guestbookId = guestbooks.get(0).getGuestbookId();
			}

			renderRequest.setAttribute("guestbookId", guestbookId);

		} catch (Exception e) {

			throw new PortletException(e);
		}

		super.render(renderRequest, renderResponse);

	}
	
	
}

