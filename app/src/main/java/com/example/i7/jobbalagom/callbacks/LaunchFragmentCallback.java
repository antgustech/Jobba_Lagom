package com.example.i7.jobbalagom.callbacks;

/**
 * Created by Kajsa, Anton, Morgan, Jakup and Christoffer.
 * Interface for callback implementation for showing the launch fragment.
 */

public interface LaunchFragmentCallback {

    /**
     * Shows different fragments depending on button click.
     *
     * @param choice the string of the button clicked.
     */
    void navigate(String choice);

    /**
     * Checks if the app has connection to server.
     *
     * @return true if there is a connection.
     */
    boolean checkConnection();
}
