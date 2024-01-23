package dao.util;

public class StatusInfo {
    public static int statusType(StatusType type){
        switch (type){
            case PENDING:return 0;
            case PROCESSING:return 1;
            case COMPLETED:return 2;
            case CLOSED:return 3;
        }
        return 0;
    }
}
