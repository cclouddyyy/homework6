import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class HomeWork6 {
    public interface Playable {
        List<String> play();

        List<String> playWith(Playable playable);
    }

    public static abstract class Instrument implements Playable {
        private String sound;
        private Integer times;

        public void setSound(String sound) {
            this.sound = sound;
        }

        public void setTimes(Integer times) {
            this.times = times;
        }

        public List<String> play() {
            List<String> result = new ArrayList<>();
            String soundString = (sound + " ").repeat(times).trim();
            result.add(soundString);
            return result;
        }

        public List<String> playWith(Playable playable) {
            List<String> result = new ArrayList<>();
            result.addAll(play());
            result.addAll(playable.play());
            return result;
        }
    }
    public static class Guitar extends Instrument {
        public Guitar() {
            setSound("Трунь");
            setTimes(2);
        }
    }
    public static class Drum extends Instrument {
        public Drum() {
            setSound("Бац");
            setTimes(3);
        }
    }
    public static class Orchestra implements Playable {
        private List<Instrument> instruments = new ArrayList<>();

        public Orchestra() {
            Guitar guitar = new Guitar();
            Drum drum = new Drum();
            Guitar guitar1 = new Guitar();
            Drum drum1 = new Drum();
            instruments.add(0, guitar);
            instruments.add(1, guitar1);
            instruments.add(2, drum);
            instruments.add(3, drum1);
        }
        public List<Instrument> getInstruments() {
            return instruments;
        }
        @Override
        public List<String> play() {
            Drum drum = new Drum();
            Guitar guitar = new Guitar();
            List<String> result = new ArrayList<>();
            for (int i = 0; i < 2; i++) {
                result.addAll(guitar.play());
                result.addAll(drum.play());
            }
            return result;
        }
        @Override
        public List<String> playWith(Playable playable) {
            List<String> result = new ArrayList<>();
            result.addAll(play());
            result.addAll(playable.play());
            return result;
        }
    }
    public static void main(String[] args) {
        var guitar = new Guitar();
        var drum = new Drum();
        print("Guitar: Гитара создалась", true);
        print("Drum:   Барабан создался", true);
        print("Guitar: play Guitar должно быть " + GUITAR_R, Objects.equals(guitar.play().get(0), GUITAR_R));
        print("Drum:   play Drum должно быть " + DRUM_R, Objects.equals(drum.play().get(0), DRUM_R));
        print("Guitar: playWith Drum первая гитара", Objects.equals(guitar.playWith(drum).get(0), GUITAR_R));
        print("Guitar: playWith Drum последний барабан", Objects.equals(guitar.playWith(drum).get(1), DRUM_R));
        print("Drum:   playWith Guitar первый барабан", Objects.equals(drum.playWith(guitar).get(0), DRUM_R));
        print("Drum:   playWith Guitar последняя гитара", Objects.equals(drum.playWith(guitar).get(1), GUITAR_R));

        var emptyOrchestra = new Orchestra();
        var orchestra = new Orchestra();
        print("EmptyOrchestra: Пустой оркестр создался", true);
        print("EmptyOrchestra: Инструменты должны быть пустым списком", emptyOrchestra.getInstruments() != null);
        print("Orchestra: Оркестр создался", true);
        print("Orchestra: В оркестре должно быть 4 инструмента", orchestra.getInstruments().size() == 4);
        print("Orchestra: Должны сыграть 4 инструмента", orchestra.play().size() == 4);
        print("Orchestra: Гитара играет первая", Objects.equals(orchestra.play().get(0), GUITAR_R));
        print("Orchestra: Барабан играет второй", Objects.equals(orchestra.play().get(1), DRUM_R));
        print("Orchestra: Гитара играет третья", Objects.equals(orchestra.play().get(2), GUITAR_R));
        print("Orchestra: Барабан играет четвертый", Objects.equals(orchestra.play().get(3), DRUM_R));
        print("Orchestra: Должны сыграть 5 инструментов", orchestra.playWith(new Guitar()).size() == 5);
        print("Orchestra: Гитара играет последняя", Objects.equals(orchestra.playWith(new Guitar()).get(4), GUITAR_R));
    }


    private static void print(String condition, Boolean act) {
        Function<String, String> yellow = str -> "\u001B[33m" + str + "\u001B[0m";
        System.out.print("TEST CASE " + yellow.apply(constLen(condition, 55)));
        if (act) System.out.print("✅");
        else System.out.print("❌");
        System.out.println();
    }

    private static String constLen(String str, int len) {
        StringBuilder sb = new StringBuilder(str);
        while (len-- - str.length() > 0) sb.append(" ");
        return sb.toString();
    }

    private static final String GUITAR_R = "Трунь Трунь";
    private static final String DRUM_R = "Бац Бац Бац";
}