package com.voitenko.dutyhelper.BL;

import android.content.Context;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.Profile;

public class UserManager {

    public static boolean isProfileValid(Context context) {
        FacebookSdk.sdkInitialize(context);
        AccessToken fb_token = AccessToken.getCurrentAccessToken();
        if(fb_token != null) {
            return true;
        } else {
            return false;
        }
    }

    public static String getFirstName() {
        Profile profile = Profile.getCurrentProfile();
        if (profile != null) {
            return profile.getFirstName();
        }
        return "";
    }

    public static String getLastName() {
        Profile profile = Profile.getCurrentProfile();
        if (profile != null) {
            return profile.getLastName();
        }
        return "";
    }

    public static String getName() {
        Profile profile = Profile.getCurrentProfile();
        if (profile != null) {
            return profile.getName();
        }
        return "";
    }

    public static String getId() {
        Profile profile = Profile.getCurrentProfile();
        if (profile != null) {
            return profile.getId();
        }
        return "";
    }

    public static String getProfile() {
        Profile profile = Profile.getCurrentProfile();
        if (profile != null) {
            return profile.getLinkUri().toString();
        }
        return "";
    }
}
