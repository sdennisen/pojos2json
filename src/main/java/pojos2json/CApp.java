package pojos2json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.util.Pair;

import java.io.File;
import java.io.IOException;
import java.security.cert.CertPathValidatorException;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/**
 * CApp
 *
 */
public final class CApp
{
    /**
     * private c'tor
     */
    private CApp()
    {
    }

    /**
     * Main method
     */
    public static void main( final String[] p_args )
    {
        // create a map of 10 pojos
        final Map<String, IPojo> l_pojos = IntStream.range( 0, 10 )
                .mapToObj( i -> new CPojo( String.valueOf( i ) ) )
                .collect( Collectors.toMap( CPojo::name, Function.identity() ) );

        // let the pojos add 10 statistical values by calling each 10 times
        IntStream
                .range( 0, 10 )
                .forEach( i -> l_pojos.values()
                        .forEach( p ->
                        {
                            try
                            {
                                p.call();
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        }) );

        final ObjectMapper l_mapper = new ObjectMapper();

        // access all fields without requiring getter/setter.
        // note: unwanted fields will have to be excluded via @JsonIgnore decorator.
        // otherwise remove following line and just leave members/methods undecorated
        l_mapper.setVisibility( PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY );

        // dump to json file
        try
        {
            l_mapper.writerWithDefaultPrettyPrinter().writeValue( new File( "pojo.json" ), l_pojos );
        }
        catch ( final IOException l_exception )
        {
            l_exception.printStackTrace();
        }
    }
}
