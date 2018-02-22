package pojos2json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
     * @param p_args
     */
    public static void main( final String[] p_args )
    {
        // create a set of 10 pojos
        final Set<CPojo> l_pojos = Collections.unmodifiableSet(
                IntStream
                        .range( 0, 10 )
                        .parallel()
                        .mapToObj( i -> new CPojo() )
                        .collect( Collectors.toSet() ) );

        // let the pojos add 10 statistical values
        IntStream
                .range( 0, 10 )
                .parallel()
                .forEach( i -> l_pojos
                        .parallelStream()
                        .forEach( CPojo::call ) );

        // dump to json file
        final ObjectMapper l_mapper = new ObjectMapper();

        // access all fields without requiring getter/setter
        l_mapper.setVisibility( PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY );

        try
        {
            l_mapper.writeValue( new File( "pojo.json" ), l_pojos );
        }
        catch ( final IOException l_exception )
        {
            l_exception.printStackTrace();
        }

    }
}
