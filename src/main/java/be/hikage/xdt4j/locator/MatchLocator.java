package be.hikage.xdt4j.locator;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import org.dom4j.Attribute;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

public class MatchLocator extends Locator {


    public MatchLocator(String parameter) {
        super(parameter);
    }

    @Override
    public String generateXPath(Element target) {
        List<String> conditionsList = new ArrayList<String>();
        StringBuilder resultXpath = new StringBuilder(target.getPath());

        resultXpath.append(generateCondition(target));


        return resultXpath.toString();

    }

    @Override
    public String generateCondition(Element target) {
        StringBuilder xPathCondition = new StringBuilder();
        List<String> conditionsList = new ArrayList<String>();

        if (!Strings.isNullOrEmpty(parameter)) {
            xPathCondition.append("[");
            String[] tokens = parameter.split(",");
            for (String attributeName : tokens) {
                Attribute attr = target.attribute(attributeName);
                StringBuilder builder = new StringBuilder();
                builder.append("@").append(attr.getName()).append("=").append("\"").append(attr.getValue()).append("\"");
                conditionsList.add(builder.toString());

            }
            Joiner.on(" and ").appendTo(xPathCondition, conditionsList);
            xPathCondition.append("]");
        }

        return xPathCondition.toString();
    }


}
