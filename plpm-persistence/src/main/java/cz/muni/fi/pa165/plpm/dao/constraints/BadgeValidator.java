package cz.muni.fi.pa165.plpm.dao.constraints;

import cz.muni.fi.pa165.plpm.entity.Badge;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BadgeValidator implements ConstraintValidator<TrainerIsNotGymLeader, Badge> {

    @Override
    public void initialize(TrainerIsNotGymLeader constraintAnnotation) {
    }

    @Override
    public boolean isValid(Badge badge, ConstraintValidatorContext constraintValidatorContext) {
        return badge != null &&
                badge.getTrainer() != null &&
                badge.getGym() != null &&
                !badge.getTrainer().equals(badge.getGym().getLeader());
    }
}
