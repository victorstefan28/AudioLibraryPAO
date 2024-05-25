// package org.pao.audiolibrarypao.constraints;
//
// import jakarta.validation.Constraint;
// import jakarta.validation.Payload;
// import org.pao.audiolibrarypao.repositories.PlaylistRepository;
// import org.springframework.beans.factory.annotation.Autowired;
//
// import java.lang.annotation.ElementType;
// import java.lang.annotation.Retention;
// import java.lang.annotation.RetentionPolicy;
// import java.lang.annotation.Target;
//
// @Constraint(validatedBy = UniquePlaylistNamePerUserValidator.class)
// @Target({ElementType.TYPE})
// @Retention(RetentionPolicy.RUNTIME)
// public @interface UniquePlaylistNamePerUser {
//    String message() default "User already has a playlist with this name";
//
//    Class<?>[] groups() default {};
//
//    Class<? extends Payload>[] payload() default {};
// }

// public class UniquePlaylistNamePerUserValidator implements
// ConstraintValidator<UniquePlaylistNamePerUser, Playlist> {
//
//    @Autowired
//    private PlaylistRepository playlistRepository;
//
//    @Override
//    public boolean isValid(Playlist playlist, ConstraintValidatorContext context) {
//        if (playlist.getUser() == null || playlist.getName() == null) {
//            return true; // Let other validations handle these cases
//        }
//
//        return playlistRepository.findByNameAndUserId(playlist.getName(),
// playlist.getUser().getId()).isEmpty();
//    }
// }
