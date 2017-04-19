/*
 * Copyright 2013 Thomas Bouffard (redfish4ktc)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package utils;

public class Check {

    public static void log(String message) {
        log("Maven invoker check", message);
    }

    public static void log(String header, String message) {
        System.out.println(String.format("[%s] %s", header, message));
    }

    public static void logAndFail(String message) {
        log(message);
        fail(message);
    }

    public static void logAndFail(String header, String message) {
        log(header, message);
        fail(message);
    }

    private static void fail(String message) throws AssertionError {
        throw new AssertionError(message);
    }
    /**
     * TravisCI 是一个基于云的持续集成项目
     * @return [description]
     */
    public static boolean isRunningOnTravisCI() {
        boolean status = false;
        log("Checking if running on Travis CI");

        String envCI = System.getenv("CI");
        log("Env CI: " + envCI);
        String envTRAVIS = System.getenv("TRAVIS");
        log("Env TRAVIS: " + envTRAVIS);

        if ("true".equals(envCI) && "true".equals(envTRAVIS)) {
            status = true;
        }
        log("Running on Travis CI: " + status);
        return status;
    }

}
