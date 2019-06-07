package com.example;

import com.google.actions.api.ActionContext;
import com.google.actions.api.ActionRequest;
import com.google.actions.api.ActionResponse;
import com.google.actions.api.Capability;
import com.google.actions.api.ConstantsKt;
import com.google.actions.api.DialogflowApp;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.MessageFormat;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.actions.api.ForIntent;
import com.google.actions.api.response.ResponseBuilder;
import com.google.actions.api.response.helperintent.NewSurface;
import com.google.actions.api.response.helperintent.Permission;
import com.google.actions.api.response.helperintent.RegisterUpdate;
import com.google.actions.api.response.helperintent.SelectionList;
import com.google.actions.api.response.helperintent.UpdatePermission;
import com.google.api.services.actions_fulfillment.v2.model.Argument;
import com.google.api.services.actions_fulfillment.v2.model.BasicCard;
import com.google.api.services.actions_fulfillment.v2.model.Button;
import com.google.api.services.actions_fulfillment.v2.model.CarouselBrowse;
import com.google.api.services.actions_fulfillment.v2.model.CarouselBrowseItem;
import com.google.api.services.actions_fulfillment.v2.model.Image;
import com.google.api.services.actions_fulfillment.v2.model.LatLng;
import com.google.api.services.actions_fulfillment.v2.model.ListSelectListItem;
import com.google.api.services.actions_fulfillment.v2.model.OpenUrlAction;
import com.google.api.services.actions_fulfillment.v2.model.OptionInfo;
import com.google.api.services.actions_fulfillment.v2.model.TableCard;
import com.google.api.services.actions_fulfillment.v2.model.TableCardCell;
import com.google.api.services.actions_fulfillment.v2.model.TableCardColumnProperties;
import com.google.api.services.actions_fulfillment.v2.model.TableCardRow;

import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DialogflowJavaDemo extends DialogflowApp {

	private static final Logger LOGGER = LoggerFactory.getLogger(DialogflowJavaDemo.class);
	private static JsonArray mapResults = null;
	private static String myLoca = null;
	private static String[] ddu=null;
	private static String destination=null;

	// Intent Handlers
	@ForIntent("Default Welcome Intent")
	public ActionResponse welcome(ActionRequest request) {
		ResponseBuilder response = getResponseBuilder(request);
		if (!request.getUserStorage().containsKey("uname")) {
			response.add("To serve you better")
					.add(new Permission()
							.setPermissions(new String[] { ConstantsKt.PERMISSION_NAME })
							.setContext("To provide a better experience"));
			return response.build();
		}
		
		return greetUser(request);
	}
	@ForIntent("handlePermission")
	public ActionResponse welcomeName(ActionRequest request) {
        LOGGER.info("in welcome Permission handler");
		boolean permissionGranted = request.getArgument(ConstantsKt.ARG_PERMISSION).getBoolValue();
		if (permissionGranted) {
			request.getUserStorage().put("uname", request.getUser().getProfile().getGivenName());
		}
		return greetUser(request);
	}

	private ActionResponse greetUser(ActionRequest request) {
		// TODO Auto-generated method stub
		String name="there";
		if(request.getUserStorage().containsKey("uname"))
			name=request.getUserStorage().get("uname").toString();
		ResponseBuilder response = getResponseBuilder(request);
		String back="Welcome";
		if(!request.getUser().getLastSeen().isEmpty()) {
			back="Welcome back!";
		}
		return response.add("Hi "+name+"!"+back+" How can I help you?").build();
	}
		
}