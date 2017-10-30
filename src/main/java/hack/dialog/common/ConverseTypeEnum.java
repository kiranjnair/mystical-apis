package hack.dialog.common;

public enum ConverseTypeEnum {
  schedule, interaction,healthtips, converse;

  public static ConverseTypeEnum fromString(String value) {
    try {
      return ConverseTypeEnum.valueOf(value);
    } catch (Exception e) {
      return null;
    }
  }



}
