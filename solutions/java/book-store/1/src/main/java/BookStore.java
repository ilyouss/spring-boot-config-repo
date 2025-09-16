import java.util.List;
import static java.util.Map.entry;
import java.util.*;
import java.util.stream.Collectors;
import java.util.function.Function;


class BookStore {

        public static final Map<Integer, Double> discountByNumber = Map.ofEntries(
            entry(1, 1.0),
            entry(2, .95),
            entry(3, .90),
            entry(4, .80),
            entry(5, .75)
    );
public Boolean convert = true;
    double calculateBasketCost(List<Integer> books) {
    if(convert){
            books = books.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).values()
                    .stream().map(e -> Integer.valueOf(e.toString())).collect(Collectors.toList());
            convert = false;
        }

        if(books.isEmpty()) {
            return 0.0;
        }
        if(books.size() == books.stream().mapToInt(Integer::intValue).sum()){
            return books.size() * 8 * discountByNumber.get(books.size());
        }
        books = books.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        List<Double> prices = new ArrayList<>();
        for(int i = books.size() ; i >= 1; i--) {
            List<Integer> bookz = new ArrayList<>();
            for(int j = 0 ; j < books.size() ; j++) {
                if(i > j)
                {
                    bookz.add(books.get(j) - 1);
                } else {
                    bookz.add(books.get(j));
                }
                bookz = bookz.stream().filter(e -> e>0).sorted(Comparator.reverseOrder()).collect(Collectors.toList());
            }
            prices.add(8 * i * discountByNumber.get(i)
                    + calculateBasketCost(bookz));
        }
        return prices.stream().mapToDouble(Double::doubleValue).min().getAsDouble();    
    }

}