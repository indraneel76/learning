#!/bin/bash

gcloud ml-engine jobs submit training cloud_mnist --module-name trainer.task --package-path trainer --job-dir JOB_DIR --region us-central1 --staging-bucket STAGING_BUCKET --stream-logs -- --data_dir DATA_DIR