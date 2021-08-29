package edu.teco.sensordatenbankmanagementsystem.util;

import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvException;
import edu.teco.sensordatenbankmanagementsystem.models.Datastream;
import edu.teco.sensordatenbankmanagementsystem.models.Observation;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Stream;
import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
public class WriteCsvToResponse {

  public static void writeObservation(PrintWriter writer, Stream<Observation> observations) {

    try {

      StatefulBeanToCsv<Observation> btcsv = new StatefulBeanToCsvBuilder<Observation>(writer)
          .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
          .withSeparator(',')
          .build();

      btcsv.write(observations);

    } catch (CsvException ex) {

      log.error("Error mapping Bean to CSV", ex);
    }
  }

  public static void writeDatastream(PrintWriter writer, List<Datastream> datastreams) {

    try {

      StatefulBeanToCsv<Datastream> btcsv = new StatefulBeanToCsvBuilder<Datastream>(writer)
          .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
          .withSeparator(',')
          .build();

      btcsv.write(datastreams);

    } catch (CsvException ex) {

      log.error("Error mapping Bean to CSV", ex);
    }
  }
}