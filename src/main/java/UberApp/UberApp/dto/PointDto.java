package UberApp.UberApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.awt.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PointDto {
    private double[] coordinates;
    private String type = "Point";
    public PointDto(double[] coordinates){
        this.coordinates = coordinates;
    }
}
