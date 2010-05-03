/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package worldclock;

/**
 *
 * @author hansolo
 */
public enum City
{
    // -12
    Wellington("Wellington", -43200000),
    Fidschi("Fidschi", -43200000),
    Kamtschatka("Kamtschatka", -43200000),
    // -11
    Magadan("Magadan", -39600000),
    Sachalin("Sachalin", -39600000),
    Salomoninseln("Salmoninseln", -39600000),
    Malekula("Malekula", -39600000),
    // -10
    Wladiwostok("Wladiwostok", -36000000),
    Guam("Guam", -36000000),
    Sydney("Sydney", -36000000),
    Brisbane("Brisbane", -36000000),
    Melbourne("Melbourne", -36000000),
    // -9.5
    Darwin("Darwin", -34200000),
    Adelaide("Adelaide", -34200000),
    // -9
    Yakutsk("Yakutsk", -32400000),
    Tokio("Tokio", -32400000),
    Seoul("Seoul", -32400000),
    Pjöngyang("Pjöngyang", -32400000),
    // -8
    UlanBatar("Ulan Batar", -28800000),
    Beijing("Beijing", -28800000),
    HongKong("Hong Kong", -28800000),
    KualaLumpur("Kuala Lumpur", -28800000),
    Perth("Perth", -28800000),
    // -7
    UlanGong("Ulan Gong", -25200000),
    Bangkok("Bangkok", -25200000),
    Hanoi("Hanoi", -25200000),
    Pedang("Pedang", -25200000),
    Jakarta("Jakarta", -25200000),
    // -6.5
    Rangun("Rangun", -23400000),
    Kokosinseln("Kokosinseln", -23400000),
    // -6
    Omsk("Omsk", -21600000),
    Novosibirsk("Novosibirsk", -21600000),
    Bischkek("Bischkek", -21600000),
    Colombo("Colombo", -21600000),
    Dakka("Dakka", -21600000),
    // -5.75
    Kathmandu("Kathmandu", -20700000),
    // -5.5
    NeuDehli("Neu Dehli", -19800000),
    Mumbai("Mumbai", -19800000),
    Kalkutta("Kalkutta", -19800000),
    Nikobaren("Nikobaren", -19800000),
    // -5
    Orenburg("Orenburg", -18000000),
    Yekaterinenburg("Yekaterinenburg", -18000000),
    Aschchabad("Aschchabad", -18000000),
    Karatschi("Karatschi", -18000000),
    // -4.5
    Kabul("Kabul", -16200000),
    // -4
    Samara("Samara", -14400000),
    Tiflis("Tiflis", -14400000),
    Eriwan("Eriwan", -14400000),
    AbuDabi("Abu Dabi", -14400000),
    Muskat("Muskat", -14400000),
    // -3.5
    Teheran("Teheran", -12600000),
    // -3
    Moskau("Moskau", -10800000),
    Damaskus("Damaskus", -10800000),
    Mogadischu("Mogadischu", -10800000),
    Nairobi("Nairobi", -10800000),
    DarEsSalam("Dar es salam", -10800000),
    // -2
    Helsinki("Helsinki", -7200000),
    Kiew("Kiew", -7200000),
    Athen("Athen", -7200000),
    Istanbul("Istanbul", -7200000),
    Kairo("Kairo", -7200000),
    Windhoek("Windhoek", -7200000),
    Kapstadt("Kapstadt", -7200000),
    // -1
    Berlin("Berlin", -3600000),
    Paris("Paris", -3600000),
    Rom("Rom", -3600000),
    Belgrad("Belgrad", -3600000),
    Algier("Algier", -3600000),
    Lagos("Lagos", -3600000),
    // +0
    London("London", 0),
    Dublin("Dublin", 0),
    Lissabon("Lissabon", 0),
    Rejkjavik("Rejkjavik", 0),
    Accra("Accra", 0),
    // +1
    Azoren("Azoren", 3600000),
    Kapverden("Kapverden", 3600000),
    // +2
    Montevideo("Montevideo", 7200000),
    // +3
    Georgetown("Georgetown", 10800000),
    Brasilia("Brasilia", 10800000),
    RioDeJaneiro("Rio de Janeiro", 10800000),
    BuenosAires("Buenos Aires", 10800000),
    // +3.5
    Neufundland("Neufundland", 12600000),
    // +4
    Halifax("Halifax", 14400000),
    Caracas("Caracas", 14400000),
    SantjagoDeChile("Santjago de Chile", 14400000),
    LaPaz("La Paz", 14400000),
    // +5
    NewYork("New York", 18000000),
    Toronto("Toronto", 18000000),
    Bogota("Bogota", 18000000),
    Lima("Lima", 18000000),
    // +6
    Saskatchewan("Saskatchewan", 21600000),
    Austin("Austin", 21600000),
    NewOrleans("New Orleans", 21600000),
    MexikoCity("Mexiko City", 21600000),
    // +7
    SaltLakeCity("Salt Lake City", 25200000),
    // +8
    Vancouver("Vancouver", 28800000),
    LosAngeles("Los Angeles", 28800000),
    SanFrancisco("San Francisco", 28800000),
    // +9
    Yukon("Yukon", 32400000),
    // +10
    Hawai("Hawai", 36000000),
    // +11
    Nome("Nome", 39600000),
    Samoa("Samoa", 39600000),
    Midwayinseln("Midwayinseln", 39600000),
    // +12
    Eniwetok("Eniwetok", 43200000),
    Kwajalein("Kwajalein", 43200000);

    private long offset;
    private String name;

    City(String name, long offset)
    {
        this.name = name;
        this.offset = offset;
    }

    public String getName()
    {
        return this.name;
    }

    public long getOffset()
    {
        return this.offset;
    }
}
