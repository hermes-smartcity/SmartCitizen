package us.idinfor.smartcitizen.exception;

import android.content.Context;

import us.idinfor.smartcitizen.R;

public class ErrorMessageFactory {

  private ErrorMessageFactory() {}

  public static String create(Context context, Exception exception) {
    String message = context.getString(R.string.exception_message_generic);

    if (exception instanceof NetworkConnectionException) {
      message = context.getString(R.string.exception_message_no_connection);
    } else if (exception instanceof UserNotFoundException) {
      message = context.getString(R.string.exception_message_user_not_found);
    }
    return message;
  }
}