package pojos2json;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

/**
 * CPojo class implementation
 */
public class CPojo implements IPojo
{

    @JsonProperty( "statistics" )
    private final List<List<Number>> m_statistics = Collections.synchronizedList( new ArrayList<>() );

    @JsonProperty( "name" )
    private final String m_name = this.toString();

    CPojo()
    {
        // add a random amount of max 10 numbers
        IntStream
                .range( 1, ThreadLocalRandom.current().nextInt( 11 ) )
                .parallel()
                .forEach( i -> m_statistics.add( Collections.synchronizedList( new ArrayList<>() ) ) );
    }

    @Override
    public IPojo call()
    {
        m_statistics
                .parallelStream()
                .forEach( s -> s.add( ThreadLocalRandom.current().nextInt( 10 ) ) );
        return this;
    }
}
