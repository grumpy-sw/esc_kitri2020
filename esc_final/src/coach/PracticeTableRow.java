package coach;

import javafx.beans.property.StringProperty;

public class PracticeTableRow {
   private StringProperty p_index;
   private StringProperty p_type;
   private StringProperty p_timestamp;
   private StringProperty p_champion;
   private StringProperty p_result;
   
   public PracticeTableRow(StringProperty t_index, StringProperty t_type, StringProperty t_timestamp, StringProperty t_champion, StringProperty t_result) {
      this.p_index = t_index;
      this.p_type = t_type;
      this.p_timestamp = t_timestamp;
      this.p_champion = t_champion;
      this.p_result = t_result;
   }
   
   public StringProperty indexProperty() {
      return p_index;
   }
   public StringProperty typeProperty() {
      return p_type;
   }
   public StringProperty timestampProperty() {
      return p_timestamp;
   }
   public StringProperty championProperty() {
      return p_champion;
   }
   public StringProperty resultProperty() {
      return p_result;
   }
   
}