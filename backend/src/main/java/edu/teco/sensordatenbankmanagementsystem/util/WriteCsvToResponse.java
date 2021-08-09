package edu.teco.sensordatenbankmanagementsystem.util;

import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvException;
import edu.teco.sensordatenbankmanagementsystem.models.Observation;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Stream;
import javax.transaction.Transactional;
import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
public class WriteCsvToResponse {

  public static void writeObservation(PrintWriter writer, List<Observation> observations) {

    try {

      ColumnPositionMappingStrategy<Observation> mapStrategy
          = new ColumnPositionMappingStrategy<>();

      mapStrategy.setType(Observation.class);

      String[] columns = new String[]{"id", "phenomenonStart", "phenomenonEnd", "resultTime",
          "type", "resultNumber", "resultString", "resultJson", "resultBool", "resultQuality",
          "validTimeStart", "validTimeEnd", "params", "featureID", "mDataStreamID"};      mapStrategy.setColumnMapping(columns);

      StatefulBeanToCsv<Observation> btcsv = new StatefulBeanToCsvBuilder<Observation>(writer)
          .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
          .withMappingStrategy(mapStrategy)
          .withSeparator(',')
          .build();

      btcsv.write(observations);

    } catch (CsvException ex) {

      log.error("Error mapping Bean to CSV", ex);
    }
  }

  public static void writeObservation(PrintWriter writer, Observation city) {

    try {

      ColumnPositionMappingStrategy<Observation> mapStrategy
          = new ColumnPositionMappingStrategy<>();

      mapStrategy.setType(Observation.class);

      String[] columns = new String[]{"id", "phenomenonStart", "phenomenonEnd", "resultTime",
          "type", "resultNumber", "resultString", "resultJson", "resultBool", "resultQuality",
          "validTimeStart", "validTimeEnd", "params", "featureID", "mDataStreamID"};
      mapStrategy.setColumnMapping(columns);

      StatefulBeanToCsv<Observation> btcsv = new StatefulBeanToCsvBuilder<Observation>(writer)
          .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
          .withMappingStrategy(mapStrategy)
          .withSeparator(',')
          .build();

      btcsv.write(city);

    } catch (CsvException ex) {

      log.error("Error mapping Bean to CSV", ex);
    }
  }
}