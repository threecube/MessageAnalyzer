package com.threecube.messageAnalyzer.parser;

/**
 * Created by wenbin_dwb on 16/8/22.
 *
 * data or message parser used into spout
 * <li>Please implement the interface for your storm input data</li>
 */
public interface ParserInterface {

    public <T> T parse (String data);
}
