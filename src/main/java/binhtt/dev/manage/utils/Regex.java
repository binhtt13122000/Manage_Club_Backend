package binhtt.dev.manage.utils;

public class Regex {
    public static final String REGEX_FULL_NAME = "^[a-zA-Z_ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶ" +
            "ẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợ" +
            "ụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s]+$";

    public static final String REGEX_STUDENT_ID = "(SE)[0-9]{6}";

    public static final String REGEX_USERNAME = "^[a-zA-Z0-9._]+$";
    public static final String REGEX_PHONE = "^\\d{10}$";

    private Regex(){
        throw new IllegalStateException("Cannot create instance of static util class");
    }
}
