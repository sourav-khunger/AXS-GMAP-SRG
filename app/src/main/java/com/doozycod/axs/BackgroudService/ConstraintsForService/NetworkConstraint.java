package com.doozycod.axs.BackgroudService.ConstraintsForService;

import androidx.work.Constraints;
import androidx.work.NetworkType;

public class NetworkConstraint {
    private static Constraints constraints = null;

    public static Constraints getNetworkConstraints() {
        if (constraints == null) {
            constraints = new Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build();
        }
        return constraints;
    }
}
