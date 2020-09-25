package com.example.modulv.tools;

import com.example.modulv.model.Auto;
import com.example.modulv.model.Dystrybutor;
import com.example.modulv.model.Klient;
import com.example.modulv.model.Producent;
import com.example.modulv.repositories.AutoRepository;
import com.example.modulv.repositories.DystrybutorRepository;
import com.example.modulv.repositories.KlientRepository;
import com.example.modulv.repositories.ProducentRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;


@Component
public class DBInflater implements ApplicationListener<ContextRefreshedEvent> {

    public DBInflater(AutoRepository autoRepository, DystrybutorRepository dystrybutorRepository, KlientRepository klientRepository, ProducentRepository producentRepository){
        this.autoRepository = autoRepository;
        this.dystrybutorRepository = dystrybutorRepository;
        this.klientRepository = klientRepository;
        this.producentRepository = producentRepository;

    }

    private AutoRepository autoRepository;
    private DystrybutorRepository dystrybutorRepository;
    private KlientRepository klientRepository;
    private ProducentRepository producentRepository;

    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) { initData();}

    private void initData() {

        Auto c1 = new Auto("C1", "2014", "Benzyna");
        Dystrybutor salonCitroen = new Dystrybutor("Citroen Bielsko-Biała", "Bielsko-Biała");
        Klient pierwszy = new Klient("Janusz", "Nowak");
        Producent Citroen = new Producent("Citroen", "Francja", "25", salonCitroen);
        c1.getProducenci().add(Citroen);
        Citroen.getAuta().add(c1);
        autoRepository.save(c1);
        dystrybutorRepository.save(salonCitroen);
        klientRepository.save(pierwszy);
        producentRepository.save(Citroen);

        Auto c3 = new Auto("C3", "2016", "Benzyna");
        Dystrybutor salonCitroen2 = new Dystrybutor("Citroen Rybink", "Rybnik");
        Klient drugi = new Klient("Jan", "Kowalski");
        Producent Citroen2 = new Producent("Citroen", "Francja", "14", salonCitroen2);
        c3.getProducenci().add(VW2);
        Citroen2.getAuta().add(c3);
        autoRepository.save(c3);
        dystrybutorRepository.save(salonCitroen2);
        klientRepository.save(drugi);
        producentRepository.save(Citroen2);

        Auto e46 = new Auto("E46", "2010", "Benzyna/LPG");
        Dystrybutor salonBMW = new Dystrybutor("BMW Gliwice", "Gliwice");
        Klient trzeci = new Klient("Marek", "Mostowiak");
        Producent BMW = new Producent("BMW", "Niemcy", "05", salonBMW);
        e46.getProducenci().add(BMW);
        BMW.getAuta().add(e46);
        autoRepository.save(e46);
        dystrybutorRepository.save(salonBMW);
        klientRepository.save(trzeci);
        producentRepository.save(BWM);

    }

}
