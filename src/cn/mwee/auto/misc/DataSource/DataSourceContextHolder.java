package cn.mwee.auto.misc.DataSource;

public class DataSourceContextHolder {
	private static String DEFAULT_KEY = "master"; 
    private static final ThreadLocal<String> contextHolder =
            new ThreadLocal<String>();
 
   public static void setDataSourceKey(String dataSourceKey) {
      contextHolder.set(dataSourceKey);
   }
 
   public static String getDataSourceKey() {
	  String dataSourceKey = (String) contextHolder.get();
      return dataSourceKey == null ? DEFAULT_KEY : (String) contextHolder.get();
   }
 
   public static void clearDataSourceKey() {
      contextHolder.remove();
   }

}
