package br.com.pedro.sistema.gestao.pagamento.core.exceptions;

public enum ErrorCode {

    // Person (PE)
    PE_0001("PE-0001", "Name cannot be null or empty."),
    PE_0002("PE-0002", "Invalid email address."),
    PE_0003("PE-0003", "Phone number cannot be null or empty."),
    PE_0004("PE-0004", "Birthday cannot be null."),

    // Student (ST)
    ST_0001("ST-0001", "Person cannot be null."),
    ST_0002("ST-0002", "Responsible cannot be null for students under 18 years old."),
    ST_0003("ST-0003", "Weight must be a positive integer."),
    ST_0004("ST-0004", "Height must be a positive integer."),
    ST_0005("ST-0005", "Health problem description cannot be null or empty when there is a health problem."),
    ST_0006("ST-0006","Cannot find student by id"),
    ST_0007("ST-0007","Student is already inactive."),
    ST_0008("ST-0008","Student is already active."),
    // Instructor (IN)
    IN_0001("IN-0001", "Person cannot be null."),
    IN_0002("IN-0002", "Instructor must be at least 18 years old."),
    IN_0003("IN-0003", "Username cannot be null or empty."),
    IN_0004("IN-0004", "Password cannot be null or empty."),
    IN_0005("IN-0005", "Belt cannot be null."),
    IN_0006("IN-0006", "Instructor cannot have a kids or youth division belt."),
    IN_0007("IN-0007","Cannot find Instructor by id"),
    IN_0008("IN-0008","Cannot find Instructor by username."),
    //ST_0007("ST-0007","Student is already inactive."),
    //ST_0008("ST-0008","Student is already active."),

    // Graduation (GR)
    GR_0001("GR-0001", "Belt cannot be null."),
    GR_0002("GR-0002", "Degree must be between 0 and 4."),
    GR_0003("GR-0003", "Cannot find graduation by belt and degree."),
    GR_0004("GR-0004", "Cannot find graduation by id."),



    // Student Graduation (SG)
    SG_0001("SG-0001", "Student cannot be null."),
    SG_0002("SG-0002", "Instructor cannot be null."),
    SG_0003("SG-0003", "Graduation cannot be null."),
    SG_0004("SG-0004", "Graduation day cannot be null."),
    SG_0005("SG-0005","Cannot find Student Graduation by id."),


    // Membership (MB)
    MB_0001("MB-0001", "Student cannot be null."),
    MB_0002("MB-0002", "Amount cannot be null."),
    MB_0003("MB-0003", "Amount must be greater than zero."),
    MB_0004("MB-0004", "Contract time cannot be null."),
    MB_0005("MB-0005", "Due date cannot be null."),
    MB_0006("MB-0006", "Reference date cannot be null."),
    MB_0007("MB-0007", "Payment date cannot be null."),
    MB_0008("MB-0008", "This membership has already been paid."),
    MB_0009("MB-0009", "Cannot cancel a paid membership."),
    MB_0010("MB-0010","Cannot find membership by id."),
    MB_0011("MB-0011","Payment status cannot be null."),


    // Responsible (RE)
    RE_0001("RE-0001", "Person cannot be null."),
    RE_0002("RE-0002", "Relationship cannot be null or empty."),
    RE_0003("RE-0003", "Student cannot be null."),
    RE_0004("RE-0004","Cannot find Responsible by id."),


    //Generic
    GE_0001("GE-0001","Id cannot be null."),
    GE_0002("GE-0002","Username cannot be null.");

    private final String code;
    private final String defaultMessage;

    ErrorCode(String code, String defaultMessage) {
        this.code = code;
        this.defaultMessage = defaultMessage;
    }

    public String getCode() {
        return code;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }

    @Override
    public String toString() {
        return "[" + code + "] " + defaultMessage;
    }
}

