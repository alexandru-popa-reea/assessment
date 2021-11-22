select
    *
from
    ASSESSMENTS A
        left join PATIENTS P on A.PATIENT_ID = P.ID
        left join USERS U on U.PATIENT_ID = P.ID
where
        U.ID = 1;