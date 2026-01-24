package System;

public enum ErrorC{
    //ERRORES
    INVALID_NUMBER("001", "[ERROR: RM-001]> Insert a valid number"),
    OUT_OF_BOUNDS("002", "[ERROR: RM-002]> Out of bounds: %s"),
    ARCHIVE_NOT_FOUND("003", "[ERROR: RM-003]> Could not find archive: %s"),
    FILE_IS_A_DIRECTORY("004", "[ERROR: RM-004]> File %s is a directory"),
    FILE_NOT_FOUND("005", "[ERROR: RM-005]> Could not find file: %s"),
    COULD_NOT_READ("006", "[ERROR: RM-006]> Couldn't read: %s"),
    INVALID_BOOT_MODE("007", "[ERROR: RM-007]> Invalid boot mode."),
    ERROR_ACCESSING_DIRECTORY("008", "[ERROR: RM-008]> Error accessing directory: %s"),
    CAN_NOT_ACCESS_DIRECTORY("009", "[ERROR: RM-009]> Cannot access directory: %s"),
    COULD_NOT_CREATE_DIRECTORY("010", "[ERROR: RM-010]> Failed to create directory: %s"),
    DIRECTORY_ALREADY_EXISTS("011", "[ERROR: RM-011]> Directory already exists: %s"),
    DIRECTORY_DOES_NOT_EXIST("012", "[ERROR: RM-012]> Directory does not exist or is a file: %s"),
    COULD_NOT_DELETE_DIRECTORY("013", "[ERROR: RM-013]> Failed to delete directory: %s"),
    INVALID_PARAMETER_FOR_FILE("014", "[ERROR: RM-014]> Invalid name or extension for file."),
    FILE_ALREADY_EXISTS("015", "[ERROR: RM-015]> File already exists: %s"),
    FAILED_TO_CREATE_FILE("016", "[ERROR: RM-016]> Failed to create file: %s"),
    FAILED_TO_ACCESS_FILE("017", "[ERROR: RM-017]> Failed to access file: %s"),
    FAILED_TO_DELETE_FILE("018", "[ERROR: RM-018]> Failed to delete file: %s"),
    FILE_DOES_NOT_EXIST("019", "[ERROR: RM-019]> File does not exist: %s"),
    INVALID_SOURCE("020", "[ERROR: RM-020]> Source file not valid: %s"),
    COULD_NOT_COPY("021", "[ERROR: RM-021]> Error while copying file: %s"),
    DESTINY_MUST_BE_AN_EXISTING_DIRECTORY("022", "[ERROR: RM-022]> Destiny must be an existent directory."),
    COULD_NOT_MOVE_FILE("023", "[ERROR: RM-023]> Could not move file to: %s"),
    UNKNOWN_COMMAND("024", "[ERROR: RM-024]> Unknown command: %s"),
    MISSING_ARGUMENT("025", "[ERROR: RM-025]> Missing argument(s)."),
    STATE_ERROR("026", "[ERROR: RM-026]> State must be ON or OFF"),
    YOU_CANNOT_DO_THAT("027", "[ERROR: RM-027]> You cannot do that in this precise moment, pal."),
    CATEGORY_DOES_NOT_EXIST("028", "[ERROR: RM-028]> Category does not exist: %s"),
    CAPABILITY_DOES_NOT_EXIST("029", "[ERROR: RM-029]> Capability does not exist: %s");

    private final String code;
    private final String template;

    ErrorC(String code, String template) {
        this.code = code;
        this.template = template;
    }

    public String getCode(){
        return code;
    }

    public String format(String detail) {
        return template.contains("%s") ? String.format(template, detail) : template;
    }
}
