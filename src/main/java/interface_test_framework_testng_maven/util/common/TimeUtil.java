package interface_test_framework_testng_maven.util.common;

public class TimeUtil {


    public static interface  Callback<T,R> {
        R apply(T object);
    }
    public static void sleep(int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static <RETURN,TYPE> RETURN wait(TYPE object, int poolingEvery,int timeout,Callback<TYPE,RETURN> callback) {
        int spent = 0;
        while(true){
            RETURN o = callback.apply(object);
            if(o != null){
                if(o instanceof Boolean){
                    if(o.equals(Boolean.valueOf(true))){
                        return o;
                    }
                }else{
                    return o;
                }
            }
            TimeUtil.sleep(poolingEvery);
            spent+=poolingEvery;
            if(spent >= timeout)
                throw new RuntimeException("wait time out for "+timeout);
        }
    }

    public static String timestamp(){
        return System.currentTimeMillis()+"";
    }
    public static String formatTimestamp(){
        // TODO: 18-4-28
        return null;
    }
//    public static void main(String[] args) {
//        int x=1;
//        TimeUtil.<Boolean,Integer>wait(x, 10, 3000, (Integer b)->{
//            return false;
//        });
//    }

}
