package com.redlee90.runexagear;

import android.util.Log;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by Basil on 8/12/2015.
 */
public class Main implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        if (!loadPackageParam.packageName.equals("com.eltechs.ed")) {
            return;
        }

        XposedBridge.log("com.eltechs.ed hooked");

        Class<?> SafetyNetResponseClass = loadPackageParam.classLoader.loadClass("com.eltechs.ed.safetyNetTamperCheck.SafetyNetResponse");
        XposedHelpers.findAndHookMethod("com.eltechs.ed.startupActions.SafetyNetTamperCheck", loadPackageParam.classLoader, "validateSafetyNetResponsePayload", SafetyNetResponseClass, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                XposedBridge.log("validateSafetyNetResponsePayload hooked");
                param.setResult(true);
            }
        });

        XposedHelpers.findAndHookMethod("com.eltechs.ed.safetyNetTamperCheck.SafetyNetResponse", loadPackageParam.classLoader, "isCtsProfileMatch", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                XposedBridge.log("validateSafetyNetResponsePayload hooked");
                param.setResult(true);
            }
        });
    }
}
