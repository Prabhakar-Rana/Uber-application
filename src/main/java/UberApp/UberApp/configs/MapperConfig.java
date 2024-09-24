package UberApp.UberApp.configs;

import UberApp.UberApp.dto.PointDto;
import UberApp.UberApp.utils.GeometryUtil;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.locationtech.jts.geom.Point;

@Configuration
public class MapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        mapper.typeMap(PointDto.class, Point.class).setConverter(context ->{   // PointDto to Point
            PointDto pointDto = context.getSource();
            return GeometryUtil.createPoint(pointDto);
        });
        mapper.typeMap(Point.class, PointDto.class).setConverter(context -> {  // Point to PointDto
            Point point = context.getSource();
            double coordinates[] = {
                point.getX(),
                point.getY()
            };
            return new PointDto(coordinates);
        });

        return mapper;
    }
}
