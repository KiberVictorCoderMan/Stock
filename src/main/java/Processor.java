import java.io.IOException;

/**
 *
 * @author andrii
 */
public interface Processor {

    void process(Request request, Response response) throws IOException;

}