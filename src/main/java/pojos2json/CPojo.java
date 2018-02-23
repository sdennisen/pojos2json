package pojos2json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonGetter;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * CPojo class implementation with some examples.
 */
public class CPojo implements IPojo
{
    // name key "statistical_data"
    @JsonProperty( "statistical_data" )
    private final List<List<Number>> m_statistics;

    // don't serialize name member to json
    @JsonIgnore
    private final String m_name;

    CPojo( final String p_name )
    {
        m_name = p_name;

        // add a random amount of max 10 number slots
        m_statistics = Collections.synchronizedList(
                IntStream
                        .range( 1, ThreadLocalRandom.current().nextInt( 11 ) )
                        .mapToObj( i -> Collections.synchronizedList( new ArrayList<Number>() ) )
                        .collect( Collectors.toList() )
        );
    }

    @Override
    public IPojo call()
    {
        // adds a random integer [0, 9] to each slot
        m_statistics.forEach( s -> s.add( ThreadLocalRandom.current().nextInt( 10 ) ) );
        return this;
    }

    public String name()
    {
        return m_name;
    }

    /**
     * Declare method statistic as a JsonGetter.
     * It lazy-calculates the statistical data of Lists (containing sub-Lists with Numbers).
     * Name the key 'statistical_summary' instead of the method name.
     */
    @JsonGetter("statistical_summary")
    public DoubleSummaryStatistics statistic()
    {
        return m_statistics
                .stream()
                .flatMap( Collection::stream )
                .collect( Collectors.summarizingDouble( Number::doubleValue ) );
    }
}
