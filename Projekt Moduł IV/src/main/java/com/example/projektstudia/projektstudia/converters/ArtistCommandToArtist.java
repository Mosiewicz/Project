package com.example.projektstudia.projektstudia.converters;

import com.example.projektstudia.projektstudia.commands.ArtistCommand;
import com.example.projektstudia.projektstudia.model.Artist;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;



@Component
public class ArtistCommandToArtist implements Converter<ArtistCommand, Artist> {
    @Synchronized
    @Nullable
    @Override
    public Artist convert(ArtistCommand source){
        if (source == null) {
            return null;
        }

        final Artist artist = new Artist();
        artist.setFirstName(source.getFirstName());
        artist.setLastName(source.getLastName());
        artist.setNick(source.getNick());

        return artist;
    }
}
